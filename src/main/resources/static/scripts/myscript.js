const courseId = document.getElementById('courseId').value

const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

const commentForm = document.getElementById('commentForm')
commentForm.addEventListener("submit", commentSubmit)
const commentsCtnr  = document.getElementById('commentCtnr');

const allComents = []

const displayComments = (comments) => {
    commentsCtnr.innerHTML = comments.map(
        (c)=> {
            return asComment(c)
        }
    ).join('')
}



async function commentSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;
    const formData = new FormData(form);

    try {
        const responseData = await postFormDataAsJson({url, formData});

        commentsCtnr.insertAdjacentHTML("afterbegin", asComment(responseData));
        form.reset();

    } catch (error) {

        let errorObj = JSON.parse(error.message);

        if (errorObj.fieldWithErrors) {
            errorObj.fieldWithErrors.forEach(
                e => {
                    let elementWithError = document.getElementById(e);
                    if (elementWithError) {
                        elementWithError.classList.add("is-invalid");
                    }
                }

            )
        }

    }
    console.log('going to submit a comment!')
}

async function postFormDataAsJson({url, formData}) {

    const plainFormData = Object.fromEntries(formData.entries());
    const formDataAsJSONString = JSON.stringify(plainFormData);

    const fetchOptions = {
        method: "POST",
        headers: {
             [csrfHeaderName] : csrfHeaderValue,
            "Content-Type" : "application/json",
            "Accept" :"application/json"
        },
        body: formDataAsJSONString
    }

    const response = await fetch(url, fetchOptions);

    if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
    }

    return response.json();
}



function asComment(c) {
   let commentHtml = `<div class="media" id="commentCntr-${c.id}">`

    commentHtml += `<div class="media-body" >`
    commentHtml += `<h4 class="media-heading">Author: ${c.author} </h4><br/>`
    commentHtml += `<small>Date: (${c.created})</small>`
    commentHtml += `<p>${c.comment}</p>`
    commentHtml += `</div>`

    return commentHtml
}


function e(type, attributes, ...content) {
    const result = document.createElement(type);

    for (let [attr, value] of Object.entries(attributes || {})) {
        if (attr.substring(0, 2) == 'on') {
            result.addEventListener(attr.substring(2).toLocaleLowerCase(), value);
        } else {
            result[attr] = value;
        }
    }

    content = content.reduce((a, c) => a.concat(Array.isArray(c) ? c : [c]), []);

    content.forEach(e => {
        if (typeof e == 'string' || typeof e == 'number') {
            const node = document.createTextNode(e);
            result.appendChild(node);
        } else {
            result.appendChild(e);
        }
    });

    return result;
}



fetch(`http://localhost:8080/api/${courseId}/comments`).then(response => response.json()).then(data => {
        for (let comment of data) {
            allComents.push(comment)
        }
        displayComments(allComents)
    }
)

