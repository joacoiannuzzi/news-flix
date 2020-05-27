$.ajax({
    url: '/secure/articles',
    dataType: "json",
    contentType: "application/json; charset=utf-8",
    type: 'get',
    data: {diario: diario},
    success: function(response){
        let articles = JSON.parse(response);
                let root = document.getElementById("article");
                for (const art of articles) {
                    var p = document.createElement("p");
                    var li = document.createElement("li");
                    p.appendChild(document.createTextNode(art.title));
                    li.appendChild(document.createTextNode(art.body));
                    root.appendChild(p);
                    root.appendChild(li);
                }
    }
});

//    function loadArticles(sel) {
//         var xhttp = new XMLHttpRequest();
//         xhttp.onreadystatechange = function() {
//             if (this.readyState === 4 && this.status === 200) {
//
//                 let articles = JSON.parse(this.response);
//
//                 let root = document.getElementById("article");
//                 for (const art of articles) {
//                     var p = document.createElement("p");
//                     var li = document.createElement("li");
//                     p.appendChild(document.createTextNode(art.title));
//                     li.appendChild(document.createTextNode(art.body));
//                     root.appendChild(p);
//                     root.appendChild(li);
//                 }
//             }
//         };
//         xhttp.open("GET", "/secure/articles",true);
//         xhttp.send(sel);
//     }