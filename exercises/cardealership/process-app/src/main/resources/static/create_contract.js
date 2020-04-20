function createContract(
    carModel,
    customerMail,
    street,
    houseNumber,
    city,
    zipCode) {
  console.log("Form submitted", carModel, customerMail, street, houseNumber,
      city, zipCode);

  // execute a REST Call to the process engine to create the process instance
  // see: https://docs.camunda.org/manual/7.12/reference/rest/message/post-message/
  // and: https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch

  const startConfig = {
    "messageName": "CarContractCreatedMessage",
    "businessKey": new Date().toDateString(),
    "resultEnabled": true,
    "processVariables": {
      "carContract": {
        "value": {
          "id": new Date().toDateString(),
          "carModel": carModel,
          "customerEmail": customerMail,
          "customerAddress": {
            "street": street,
            "houseNumber": houseNumber,
            "zipCode": zipCode,
            "city": city
          }
        }
      }
    }
  };

  fetch('/rest/message', {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(startConfig)
  }).then((response) => {
    return response.json();
  }).then((responseJson) => {
    console.log("ProcessInstance object:", responseJson);
    const processInstanceId = responseJson[0].processInstance.id;
    console.log("ProcessInstanceId: ", processInstanceId);

    // Display a toast and change page to index.html
    $('#contractCreatedToast').on('hidden.bs.toast', function () {
      window.location.assign("index.html");
    });
    $('#contractCreatedToastBody').append(
        ' ProcessInstanceId: ' + processInstanceId);
    $('#contractCreatedToast').toast('show');
  });

}
