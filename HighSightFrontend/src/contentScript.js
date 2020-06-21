let body = document.body
let instance = new Mark(body);

let options = {"separateWordSearch": false,
    "acrossElements" : true,
    "ignoreJoiners": true,
    "each": function(element) {
        setTimeout(function() {
            element.classList.add("animate");
        }, 250);
    }
}

let selectedText = null;

window.onload = function () {
    console.log("page load!");
    console.log(window.location.href)
    ping();

    const head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
    const script1 = document.createElement('script');
    script1.setAttribute("type", "text/javascript");
    script1.setAttribute("src", chrome.extension.getURL('html5tooltips/tooltipPrivateMethods.js'));
    head.insertBefore(script1, head.lastChild);

    const script2 = document.createElement('script');
    script2.setAttribute("type", "text/javascript");
    script2.setAttribute("src", chrome.extension.getURL('html5tooltips/html5tooltips.js'));
    head.insertBefore(script2, head.lastChild);
}

document.addEventListener('closeAllToolTips', function (e) {
    console.log('received close');
    closeTooltip();
});


document.addEventListener('useful', function (e) {
    console.log('received useful');
    addTag('useful');
});

document.addEventListener('inaccurate', function (e) {
    console.log('received inaccurate');
    addTag('inaccurate')
});


// Lets listen to mouseup DOM events.
document.addEventListener('mouseup', function (e) {
    let selectedElement = window.getSelection();
    selectedText = selectedElement.toString();

    if (selectedText.length > 0 && !getTooltip()) {
        let tooltip = new HTML5TooltipUIComponent;
        let node = selectedElement.getRangeAt(0).startContainer.parentNode;
        tooltip.set({
            animateFunction: "spin",
            color: "black",
            contentText: `<a onclick="document.dispatchEvent(new CustomEvent('closeAllToolTips', {}));">×</a><br>
                          <div>
                            <button id="useful" onclick="document.dispatchEvent(new CustomEvent('useful', {})); "> Useful </button>
                            <button id="inaccurate" onclick="document.dispatchEvent(new CustomEvent('inaccurate', {})); "> Inaccurate </button> 
                          </div>`,
            stickTo: "top",
            target: node
        });

        tooltip.mount();
        tooltip.show();
        includeToolTip(tooltip);
    }
}, false);

// Close the tooltips when we click on the screen.
document.addEventListener('click', function (event) {
    if (window.getSelection().toString().length > 0) {
        return
    }
    let targetElement = event.target; // clicked element
    do {
        if (getTooltip() === targetElement) {
            // This is a click inside. Do nothing, just return.
            document.getElementById("flyout-debug").textContent = "Clicked inside!";
            return;
        }
        // Go up the DOM
        targetElement = targetElement.parentNode;
    } while (targetElement);
    // this is a click outside
    closeTooltip();
}, false);

function ping() {
    chrome.runtime.sendMessage('ping', response => {
        console.log("sending ping")
        if (chrome.runtime.lastError) {
            console.log("background script not ready..setting timeout")
            setTimeout(ping, 1000);
            console.log("timeout done..")
        } else {
            console.log("sending actual message")
            chrome.runtime.sendMessage(
                {contentScriptQuery: 'fetchHighlights', url: window.location.href},
                responseObj => {
                    console.log(responseObj);
                    responseObj.forEach(highlight => {
                        instance.mark(highlight.content, options);
                    })
                });
        }
    });
}

function addTag(tag) {
    chrome.runtime.sendMessage(
        {contentScriptQuery: 'addTag', url: window.location.href, tag: tag, content: selectedText},
        responseObj => {
            console.log(responseObj);
            instance.mark(responseObj.content, options);
            closeTooltip();
        });
}
