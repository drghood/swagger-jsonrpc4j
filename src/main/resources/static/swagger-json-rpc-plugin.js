window.onload = function() {

    let requestId = 1;

    function jsonRpcRequestInterceptor(request) {
    	
        if (request.url !== null && request.url.startsWith("http")) {
        	const lastSlash = request.url.lastIndexOf("/");
        	const methodName = request.url.slice(lastSlash + 1);
            request.url = request.url.slice(0, lastSlash);
            var params = null;
            if(request.body){
            	params = [];
            	params.push(JSON.parse(request.body));
            }
            request.body = JSON.stringify({
                id: requestId++,
                method: methodName,
                params: params,
                jsonrpc: '2.0'
            }, null, 2);
            request.headers['Content-Type'] = 'application/json';
        }

        return request;
    }

    function createBeautifulJsonRpcMarker() {
        return {
            wrapComponents: {
                DeepLink: (Original, { React }) => (props) => {
                	let groupStart = '[JsonRpc4J] ';
                    if (props['text'] && props['text'].startsWith(groupStart)) {
                        const jsonRpcProps = Object.assign({}, props);
                        jsonRpcProps['text'] = props['text'].slice(groupStart.length);

                        return React.createElement('div', null,
                            React.createElement('span', {className: 'json-rpc'}, 'JsonRpc4J'),
                            React.createElement(Original, jsonRpcProps)
                        );
                    }

                    return React.createElement(Original, props);
                }
            }
        }
    }

    const ui = SwaggerUIBundle({
        url: "v3/api-docs",
        dom_id: '#swagger-ui',
        layout: "StandaloneLayout",
        deepLinking: true,
        displayRequestDuration: true,
        docExpansion: "none",
        plugins: [
            SwaggerUIBundle.plugins.DownloadUrl,
            createBeautifulJsonRpcMarker
        ],
        presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
        ],
        requestInterceptor: jsonRpcRequestInterceptor
    });

    window.ui = ui;
};
