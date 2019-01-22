function validateForm() {
    var login = document.getElementById("login");
    var password = document.getElementById("password");
    validateElement(login);
    validateElement(password);
}

function validateElement(element) {

    if (element.value.length < 4) {
        element.class = "invalid";
    } else {
        element.class = "valid";
    }
    var message = element.name + ":" + element.value + "," + element.value.length;

}