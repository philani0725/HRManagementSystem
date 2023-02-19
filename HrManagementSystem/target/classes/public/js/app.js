 window.addEventListener('load', () => {
    const app = $('#app');

    const loginTemplate = Handlebars.compile($('#Login-template').html());
    const employeeTemplate = Handlebars.compile($('#employees-template').html())
    const newEmployeeTemplate = Handlebars.compile($('#newemployee-template').html())
    const employeeIdTemplate = Handlebars.compile($('#employeeId-template').html());
    const departmentIdTemplate = Handlebars.compile($('#departmentId-template').html());
    const departmentTemplate = Handlebars.compile($('#departments-template').html())
    const newDepartmentTemplate = Handlebars.compile($('#newdepartment-template').html())
    const HomeTemplate = Handlebars.compile($('#home-template').html())


    const router = new Router({
        mode: "hash",
        root:'index.html',
        page404: (path) => {
            const html = loginTemplate();
            app.html(html);
            loginUser();
        }
    });

    router.add('/employees', async () => {
        html = employeeTemplate();
        app.html(html);
        lookupEmployees();
    });

    router.add('/home', async () => {
            html = HomeTemplate();
            app.html(html);
            lookupHome();
        });

    router.add('/newemployee', async () => {
        html = newEmployeeTemplate();
        app.html(html);
        addEmployee();
    });

    router.add('/employeeId', async () => {
        html = employeeIdTemplate();
        app.html(html);
    });

    router.add('/departments', async () => {
        html = departmentTemplate();
        app.html(html);
        lookupDepartments();
    });

    router.add('/newdepartment', async () => {
        html = newDepartmentTemplate();
        app.html(html);
        addDepartment();
    });

    router.add('/departmentId', async () => {
        html = departmentIdTemplate();
        app.html(html);
    });

    router.add('/login', async () => {
        html = loginTemplate();
        app.html(html);
        loginUser();
    });

    router.addUriListener();

    $('a').on('click', (event) => {
        event.preventDefault();
        const target = $(event.target);
        const href = target.attr('href');
        const path = href.substring(href.lastIndexOf('/'));
        router.navigateTo(path);
    });

    router.navigateTo('/login');
});



function lookupHome() {

   const id = sessionStorage.getItem("userID");
   console.log(id);
   const options = methodCallGet();

   fetch(`/home/person/${id}`, options)
        .then(response => response.json())
        .then(data => {

            data = {
                employees : data,

            };
            console.log(data);
            executeTemplate('home-template', data, 'app');

        });
}


/**
 * It fetches the employees for the logged in user and then executes the employees-template with the data.
 */
function lookupEmployees() {

   const id = sessionStorage.getItem("userID");
   console.log(id);
   const options = methodCallGet();

   fetch(`/employees/person/${id}`, options)
        .then(response => response.json())
        .then(data => {

            data = {
                employees : data,

            };
            console.log(data);
            executeTemplate('employees-template', data, 'app');

        });
}

function lookupDepartments() {

   const id = sessionStorage.getItem("userID");
   console.log(id);
   const options = methodCallGet();

   fetch(`/departments/person/${id}`, options)
        .then(response => response.json())
        .then(data => {

            data = {
                departments : data,

            };
            console.log(data);
            executeTemplate('departments-template', data, 'app');

        });
}

/**
 * It returns an object with a method property set to GET.
 * @returns An object with a method property set to GET.
 */
function methodCallGet(){
    const options = {
        method: 'GET',
    };
    return options;
}


/**
 * It returns the user ID of the logged in user
 * @returns The userID of the logged in user.
 */
function loggedInPersonId(){
    return document.getElementById('userID').innerText;
}

/**
 * The function takes the email address from the login form and sends it to the server. The server then returns the user's
 * ID and email address. The ID is stored in session storage and the email address is used to populate the navigation bar
 */
function loginUser() {
    const form = document.getElementById("login-form");
    form.addEventListener("submit", (event) => {
        event.preventDefault();

        const data = new FormData(event.target);
        const word = data.get("email");
        const pass = data.get("password");

        const options = {
            method: 'POST',
            body: `{"email": "${word}"
            ,"password": "${pass}"}`
        };

        fetch(`/people`, options)
            .then(response => response.json())
            .then(data => {
                sessionStorage.setItem("userID", data.id);
                sessionStorage.setItem("username", data.email.substring(0,data.email.indexOf("@")));
                sessionStorage.setItem("password", data.password);
                data = {
                    email: data.email,
                    id: data.id,
                    password: data.password
                };
                executeTemplate('navigation-template', data, 'nav');
                window.location.href="#/home"

        });
    });
}





/**
 * It takes a template, data, and an element to get, and then it compiles the template with the data and puts the result in
 * the element to get
 * @param templates - the id of the template you want to use
 * @param data - This is the data that you want to pass to the template.
 * @param elementToGet - This is the element that you want to put the compiled template into.
 */
function executeTemplate(templates, data, elementToGet){
    const template = document.getElementById(templates).innerText;
    const compiledFunction = Handlebars.compile(template);
    document.getElementById(elementToGet).innerHTML = compiledFunction(data);
}



/**
 * The function adds an event listener to the form, which prevents the default action of the form, gets the data from the
 * form, converts the date to the correct format, and then sends a POST request to the server with the data
 */
function addEmployee() {
    const form = document.getElementById("newEmployee-form");
    const id = document.getElementById('userID').innerText;
    form.addEventListener("submit", (event) => {
        event.preventDefault();

        const data = new FormData(event.target);
        const firstname = data.get("firstname");
        const lastname = data.get("lastname");
        const phone = data.get("phone");
        const email = data.get("email");
        const manager = data.get("manager");
        const status = data.get("status");
        const password = "Password123#";

        const options = {
            method: 'POST',
            body: `{"adminId": ${id},
                     "FIRSTNAME": ${firstname},
                     "LASTNAME": ${lastname},
                     "TELNO": ${phone},
                     "EMAIL": ${email},
                     "EMPLOYEEMANAGER": ${manager},
                     "STATUS": ${status},
                     "PASSWORD": ${password}
                    }`
        };

        alert("Saved Successfully. Navigate to employees to view.");


        fetch(`/employees`, options)
                .then(response => response.json())

        form.reset();
    });
}


/**
 * The function adds an event listener to the form, which prevents the default action of the form, gets the data from the
 * form, converts the date to the correct format, and then sends a POST request to the server with the data
 */
function addDepartment() {
const form = document.getElementById("newDepartment-form");
    const id = document.getElementById('userID').innerText;
    form.addEventListener("submit", (event) => {
        event.preventDefault();

        const data = new FormData(event.target);
        const name = data.get("name");
        const manager = data.get("manager");
        const status = data.get("status");


        const options = {
            method: 'POST',
            body: `{"adminId": ${id},
                     "NAME": ${name},
                     "manager": ${manager},
                     "status": ${status}
                    }`
        };

        alert("Saved Successfully. Navigate to departments to view.");

            fetch(`/departments`, options)
                .then(response => response.json())

        form.reset();
    });
}



/**
 * This function takes an employeeId as a parameter, makes a GET request to the server, and returns the employees's
 * description
 * @param employeeId - the id of the employees you want to get the name of
 * @returns The description of the employees.
 */
function getEmployeeName(employeeId){
   const options = methodCallGet();

    let firstname;
    let lastname;

    return fetch(`/employees/${employeeId}`, options)
        .then(response => response.json())
        .then(data => {
            firstname = data.firstname;
            lastname = data.lastname;

            return firstname +" "+lastname;
        });
}

function getDepartmentName(departmentId){
   const options = methodCallGet();

    let name;
    let status;
    let manager;
    let adminId;

    return fetch(`/departments/${departmentId}`, options)
        .then(response => response.json())
        .then(data => {
            name = data.name;
            status = data.status;
            manager = data.manager;
            adminId = data.adminId;

            return data;
        });
}




//Getting name of person using personID Api
function getName(personID){

    const options = methodCallGet();

    const personName = fetch(`/people/${personID}`, options)
        .then(response => response.json())
        .then(data => data.email.split("@")[0]);

    return personName;

};

//Getting employees description
function getDescription(employeeID){

    const options = methodCallGet();

    const description = fetch(`/employees/${employeeID}`, options)
        .then(response => response.json())
        .then(data => data);

    return description;

};


function getDescriptionD(departmentIdID){

    const options = methodCallGet();

    const descriptionD = fetch(`/departments/${departmentID}`, options)
        .then(response => response.json())
        .then(data => data);

    return descriptionD;

};






