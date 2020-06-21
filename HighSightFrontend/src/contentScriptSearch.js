let options = {
    "separateWordSearch": false,
    "acrossElements": true,
    "ignoreJoiners": true
}

function coloredOptions(cssClass) {
    options["each"] =
        function (element) {
            setTimeout(function () {
                element.classList.add("animate");
                element.classList.add(cssClass);
            }, 250);
        }
    return options;
}

window.onload = function () {
    console.log("Search script activated");
    ping();
}

function ping() {
    chrome.runtime.sendMessage('ping', response => {
        console.log("sending ping")
        if (chrome.runtime.lastError) {
            console.log("background script not ready..setting timeout")
            setTimeout(ping, 1000);
            console.log("timeout done..")
        } else {
            console.log("sending fetchPages message")
            const links = document.getElementById('search').getElementsByTagName('a');
            for (let i = 0; i < links.length; i++) {
                let h3s = links[i].getElementsByTagName("h3");
                if (h3s.length > 0) {
                    let urlWithText = {"url": links[i].href, "text": h3s[0].textContent}
                    chrome.runtime.sendMessage(
                        {contentScriptQuery: 'fetchPage', input: urlWithText},
                        responseObj => {
                            console.log(responseObj)
                            let url = "a[href='" + responseObj.url + "']";
                            let element = document.querySelectorAll(url);
                            let instance = new Mark(element);
                            if (responseObj.overallScore === "POSITIVE") {
                                instance.mark(responseObj.content, coloredOptions("positive"));
                            } else if (responseObj.overallScore === "NEGATIVE") {
                                instance.mark(responseObj.content, coloredOptions("negative"));
                            } else if (responseObj.overallScore === "NEUTRAL") {
                                instance.mark(responseObj.content, coloredOptions("neutral"));
                            }
                        });
                    console.log(urlWithText);
                }
            }
        }
    });
}
