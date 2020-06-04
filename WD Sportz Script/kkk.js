


const request = require('request');


var HomeTeam = []
var AwayTeam = []
var i,j

function getteams (){


    request('https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php/?id=4647', { json: true }, (err, res, body) => {
        if (err) { return console.log(err); }


            console.log("home");
            for (i = 0; i < body.events.length; i++) {

                // console.log(response.data.events[i].idHomeTeam);
                HomeTeam [i] = body.events[i].idHomeTeam;h
                HomeTeam.push(body.events[i].idHomeTeam)
                // IDs.push(body.events[i].idHomeTeam);
                console.log(HomeTeam[i]);
            }

            console.log("Away");
            for (j = 0; j < body.events.length; j++) {
                // console.log(response.data.events[j].idAwayTeam);
                AwayTeam [j] = body.events[j].idAwayTeam;
                console.log(AwayTeam[j]);
            }

            HomeTeam.push(i)


        })

    // console.log("test");
    // console.log(HomeTeam[0]);


        // .catch(error => {
        //     console.log(error);
        // });


}

    getteams();

console.log("test");
console.log(HomeTeam[0]);
//
// console.log("test");
// console.log(HomeTeam[0]);

// axios.get('https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=137953')
//     .then(response => {
//         // let events =  console.log(response.data.events[0].idHomeTeam);
//         var i;
//
//
//         for (i = 0; i < response.data.events.length; i++) {
//             // console.log(response.data.events[i].idHomeTeam);
//             HomeTeam [i] = response.data.events[i].idHomeTeam;
//             console.log(HomeTeam[i])
//         }
//
//         console.log("Away");
//         for (j = 0; j < response.data.events.length; j++) {
//             // console.log(response.data.events[j].idAwayTeam);
//             AwayTeam [j] = response.data.events[j].idAwayTeam;
//             console.log(AwayTeam[j])
//         }
//
//         return
//     })
//     .catch(error => {
//         console.log(error);
//     });

// console.log('test')
// console.log(HomeTeam[0])

// const superagent = require('superagent');
//
// superagent.get('https://www.thesportsdb.com/api/v1/json/1/lookupteam.php')
//     .query({ id:HomeTeam[0]})
//     .end((err, res) => {
//         if (err) {
//             return console.log(err);
//         }
//         console.log(res);
//     })

