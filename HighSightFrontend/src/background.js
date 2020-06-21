const BASE_URL = "http://localhost:8080/"
chrome.runtime.onConnect.addListener(port => {
    port.onMessage.addListener(msg => {
            console.log("received message");
            console.log(msg);
        }
    );
})

chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    if (request.contentScriptQuery === "fetchHighlights") {
        console.log("received message");
        let url = BASE_URL + "highlights/get";
        let data = '{"url":"' + request.url + '"}'
        fetch(url,
            {
                headers: {
                    "Content-type": "application/json"
                },
                method: "POST",
                body: data
            })
            .then(
                function (response) {
                    if (response.status !== 200) {
                        console.error('Looks like there was a problem. Status Code: ' +
                            response.status);
                        return;
                    }

                    // Examine the text in the response
                    response.json().then(function (data) {
                        console.log(data);
                        sendResponse(data)
                    });
                }
            )
            .catch(function (err) {
                console.log('Fetch Error :-S', err);
            });
    }
    else if (request.contentScriptQuery === "addTag") {
        console.log("received addTag message");
        let isUseful = request.tag === 'useful'
        let url = BASE_URL + "highlights/add";
        let data = '{"url":"' + request.url + '", "content":"' + request.content + '", "useful":' + isUseful + '}'
        fetch(url,
            {
                headers: {
                    "Content-type": "application/json"
                },
                method: "POST",
                body: data
            })
            .then(
                function (response) {
                    if (response.status !== 200) {
                        console.error('Looks like there was a problem. Status Code: ' +
                            response.status);
                        return;
                    }

                    // Examine the text in the response
                    response.json().then(function (data) {
                        console.log(data);
                        sendResponse(data)
                    });
                }
            )
            .catch(function (err) {
                console.log('Fetch Error :-S', err);
            });
    }
    else if (request.contentScriptQuery === "fetchPage") {
        console.log("received fetchPages message");
        let url = BASE_URL + "pages/get"
        let data = '{"url":"' + request.input.url + '"}';
        fetch(url,
            {
                headers: {
                    "Content-type": "application/json"
                },
                method: "POST",
                body: data
            })
            .then(
                function (response) {
                    if (response.status !== 200) {
                        console.error('Looks like there was a problem. Status Code: ' +
                            response.status);
                        return;
                    }

                    // Examine the text in the response
                    response.json().then(function (data) {
                        console.log(data);
                        sendResponse({"overallScore": data.overallScore, "content": request.input.text, "url": request.input.url})
                    });
                }
            )
            .catch(function (err) {
                console.log('Fetch Error :-S', err);
            });

    } else {
        sendResponse("pong")
    }
    return true;
});
