var ws = new WebSocket("ws://localhost:8080/reminders/changes");

function addListItem(data) {
    var liElm = document.createElement('li');
    liElm.className =
        'list-group-item';
    liElm.innerHTML = data.caption;
    document.getElementById('list').appendChild(liElm);
}

ws.onmessage = function (event) {
    console.log(event.data);
    addListItem(JSON.parse(event.data));
};