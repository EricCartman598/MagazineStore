$(document).ready(function () {
    $("#topUpBalanceForm").submit(function (e) {
        e.preventDefault();
        var amount = $("#amount").val();
        if (amount === "" || amount <= 0) {
            alert("wrong amount!");
        } else {
            $("#topUpBalanceForm").unbind("submit").submit();
        }
    });

    $("#changePasswordForm").submit(function (e) {
        e.preventDefault();
        var oldPassword = $("#oldPassword").val().trim();
        var newPassword = $("#newPassword").val().trim();
        var repeatedNewPassword = $("#repeatedNewPassword").val().trim();
        if (oldPassword === "" || newPassword === "" || repeatedNewPassword === "") {
            alert("empty fields!");
        } else if (newPassword !== repeatedNewPassword) {
            alert("Passwords masmatch!");
        } else {
            $("#changePasswordForm").unbind("submit").submit();
        }
    });

    $("#editDataForm").submit(function (e) {
        e.preventDefault();
        var inputFirstName = $("#inputFirstName").val().trim();
        var inputLastName = $("#inputLastName").val().trim();
        var inputBirthDate = $("#inputBirthDate").val().trim();
        var inputEmail = $("#inputEmail").val().trim();
        if (inputFirstName === "" || inputLastName === "" || inputBirthDate === "" || inputEmail === "") {
            alert("empty fields!");
        } else {
            $("#editDataForm").unbind("submit").submit();
        }
    });

    $("#registrationForm").submit(function (e) {
        e.preventDefault();
        var inputFirstName = $("#inputFirstName").val().trim();
        var inputLastName = $("#inputLastName").val().trim();
        var inputBirthDate = $("#inputBirthDate").val().trim();
        var inputEmail = $("#inputEmail").val().trim();
        var inputPassword = $("#inputPassword").val().trim();
        var inputRepeatedPassword = $("#inputRepeatedPassword").val().trim();

        if (inputFirstName === "" ||
            inputLastName === "" ||
            inputBirthDate === "" ||
            inputEmail === "" ||
            inputPassword === "" ||
            inputRepeatedPassword === "") {
            alert("empty fields!");
        } else if(inputPassword !== inputRepeatedPassword) {
            alert("Passwords masmatch!");
        } else {
            $("#registrationForm").unbind("submit").submit();
        }
    });
});
