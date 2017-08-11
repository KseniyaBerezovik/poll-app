function addInput() {
    var container = document.getElementById("container");
    var input = document.createElement("input");
    input.type = "text";
    input.className = "optionName form-control";
    container.appendChild(input);
}

function addPoll() {
    addPollName();
    addPollOptions();
    window.location.replace("http://localhost:8080/polls");
}

function addPollName() {
    var pollName = document.getElementById("pollName").value;
    $.ajax("/add-poll-name", {
        method: "POST",
        contentType: "application/json",
        async: false,
        data: JSON.stringify({
            title: pollName
        })
    });
}

function addPollOptions() {
    var optionNames = document.getElementsByClassName("optionName");
    var names = [];
    for(var i = 0; i < optionNames.length; i++) {
        names.push({"name" : optionNames[i].value})
    }

    $.ajax("/add-poll-options", {
        method: "POST",
        contentType: "application/json",
        async: false,
        data: JSON.stringify(names)
    })
}

function vote() {
    var voteItems = document.getElementsByClassName("voteItems");
    var ids = [];
    for (i = 0; i < voteItems.length; i++) {
        if(voteItems[i].checked) {
            ids.push({"id" : voteItems[i].value});
        }
    }
    $.ajax("/poll-vote", {
        method: "POST",
        contentType: "application/json",
        async: false,
        data: JSON.stringify(ids)
    });
    window.location.replace("http://localhost:8080/vote-success");
}