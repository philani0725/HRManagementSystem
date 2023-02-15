window.addEventListener('load', () => {
    const app = $('#app');

    const loginTemplate = Handlebars.compile($().html());
    const CreateDeleteEmployee = Handlebars.compile($().html());
    const CreateDeleteManager = Handlebars.compile($().html());
    const EmployeeList = Handlebars.compile($().html());
    const DepartmentsList = Handlebars.compile($().html());

      const router = new Router({
            mode: "hash",
            root:'index.html',
            page404: (path) => {
                const html = loginTemplate();
                app.html(html);
                loginUser();
            }
        });
}