console.log("asdasdqwe");


function check_checkbox() {
    if ($('#terms').is(':checked')) {
        return true;
    } else {
        alert("За да продължиш, маркирай чекбокса!");
        return false;
    }
}

function search() {
    var searchbar = document.getElementById("searchBar");


}


const courseId = document.getElementById('courseId').value;
const commentsContainer = document.getElementById('commentCtnr');

const allComents = []

const displayComments = (comments) => {
    commentsContainer.innerHTML = comments.map(
        (c)=> {
            return asComment(c)
        }
    ).join('')
}

function asComment(c) {
    let commentHtml = `<div id="commentCntr-${c.id}">`

    commentHtml += `<h4>${c.author} (${c.created})</h4><br/>`
    commentHtml += `<p>${c.comment}</p>`
    commentHtml += `</div>`

    return commentHtml
}





fetch(`http://localhost:8080/api/${courseId}/comments`).then(response => response.json()).then(data => {
        for (let comment of data) {
            allComents.push(comment)
        }
        displayComments(allComents)
    }
)

