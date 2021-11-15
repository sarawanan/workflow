async function post(url = '') {
  const response = await fetch(url, {
    method: 'POST',
    mode: 'cors',
    cache: 'no-cache',
    credentials: 'same-origin',
    headers: {
      'Content-Type': 'application/json'
    },
    redirect: 'follow',
    referrerPolicy: 'no-referrer',
    body: null
  });
  return response;
}

function $(elementName) {
    return document.getElementById(elementName);
}

$('initiateMemo').addEventListener('click', () => {
    $('initiateMemo').disabled = true;
    $('message').innerText = 'New memo has been initiated. Please check the status after some time';
    post('http://localhost:8080/initiate').then(response => {
        document.location.href = 'http://localhost:8080/';
    });
})

$('resetTimer').addEventListener('click', () => {
    post('http://localhost:8080/reset-timer').then(response => {
        document.location.href = 'http://localhost:8080/';
    });
})

$('refresh').addEventListener('click', () => {
    location.reload();
})

document.querySelectorAll('#retry').forEach(e => {
    e.addEventListener('click', onClickEvent);
})

function onClickEvent(e) {
    const url = 'http://localhost:8080/retry-memo?id=' + e.target.alt;
    post(url).then(response => {
        document.location.href = 'http://localhost:8080/';
    });
}