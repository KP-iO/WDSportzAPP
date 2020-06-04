const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

var admin = require("firebase-admin");

var serviceAccount = require("path/to/serviceAccountKey.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://wdsportz-3e91f.firebaseio.com"
});



var event = {
    homeid: null,
    homelogo: null,
    awayid: null,
    homelogo: null
};

var EventsArr = [];

// var HomeTeamArr = [];
// var AwayTeamArr = [];


const Team_ID = () => {

    const nxhr = new XMLHttpRequest();

    const baseURL = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php/?id=4647";

    nxhr.open("GET", baseURL, false);

    nxhr.send();

    // nxhr.onreadystatechange = function() {

    if (nxhr.readyState === 4 && nxhr.status === 200) {
        // console.log(this.responseText);
        let response = JSON.parse(nxhr.responseText);

        //for each event define a object and add to array of objects (list of events)
        for (i = 0; i < response.events.length; i++) {

            //var newEventObj = "event".concat(response.idEvent);

            newEvent = Object.create(event);

            //push each new object in array
            EventsArr.push(newEvent);

            EventsArr[i].homeid = response.events[i].idHomeTeam;
            EventsArr[i].awayid = response.events[i].idAwayTeam;

            console.log(EventsArr[i]);


        }

    }
}

Team_ID();

// //     //   Images


const Team_Logo = () => {
    for (j = 0; j < EventsArr.length; j++) {

        idImg2Get_home = EventsArr[j].homeid;
        //console.log("test");
        //console.log(EventsArr[j].homeid);

        const nxhr = new XMLHttpRequest();

        var partialUrl = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=";

        var teamIdUrl = idImg2Get_home;

        var completeUrl = partialUrl.concat(teamIdUrl);

        const baseURL = completeUrl;

        nxhr.open("GET", baseURL, false);

        nxhr.send();

        if (nxhr.readyState === 4 && nxhr.status === 200) {

            //console.log(nxhr.responseText);
            let response = JSON.parse(nxhr.responseText);
            EventsArr[j].homelogo = response.teams[0].strTeamBadge;

            console.log(EventsArr[j]);

        }
    }




    ///AWAY


    for (y = 0; y < EventsArr.length; y++) {

        idImg2Get_away = EventsArr[y].awayid;
        //console.log("test");
        //console.log(EventsArr[y].homeid);

        const nxhr = new XMLHttpRequest();

        var partialUrl = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=";

        var teamIdUrl = idImg2Get_away;

        var completeUrl = partialUrl.concat(teamIdUrl);

        const baseURL = completeUrl;

        nxhr.open("GET", baseURL, false);

        nxhr.send();

        if (nxhr.readyState === 4 && nxhr.status === 200) {

            //console.log(nxhr.responseText);
            let response = JSON.parse(nxhr.responseText);
            EventsArr[y].awaylogo = response.teams[0].strTeamBadge;
            //console.log(response.teams[0].strTeamBadge);

            console.log(EventsArr[y]);


        }
    }
}

Team_Logo();

//concat test
console.log(EventsArr[2].homelogo);