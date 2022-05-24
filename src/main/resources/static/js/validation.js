const isFiledValueValid = (value) => value.trim().length > 0;
const isPasswordValid = (password) => {
    const re = new RegExp("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,20}$");
    return re.test(password);
};
const isValid = () => {
    return checkRequiredField($("#inputFirstName")) && checkRequiredField($("#inputLastName"))
        && checkRequiredField($("#inputUsername")) && checkPassword($("#inputPassword").val())
}

const checkRequiredField = (field) => {

    if (!isFiledValueValid(field.val())) {
        alert(field.attr('name').toUpperCase() + " is required and can not be blank!")
        return false;
    }
    return true;
}

const checkPassword = (password) => {
    // if (!isPasswordValid(password)) {
    //     alert("PASSWORD must between 8 to 20 and include lower and upper letter and digit and at least one of the following !@#$%^&*")
    //     return false;
    // }
    return true;
}

//
// $("form").submit(function (e) {
//
// isValid()
// })


