function createContract(
    carModel,
    customerMail,
    street,
    houseNumber,
    city,
    zipCode) {
  console.log("Form submitted", carModel, customerMail, street, houseNumber,
      city, zipCode);

  //TODO execute a REST Call to the process engine to create the process instance
  // see: https://docs.camunda.org/manual/7.12/reference/rest/message/post-message/
  // and: https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch
  let processInstanceId = "TBD" //TODO assign the process instance id returned by the request to this variable

  // Display a toast and change page to index.html
  $('#contractCreatedToast').on('hidden.bs.toast', function () {
    window.location.assign("index.html");
  });
  $('#contractCreatedToastBody').append(
      ' ProcessInstanceId: ' + processInstanceId);
  $('#contractCreatedToast').toast('show');
}