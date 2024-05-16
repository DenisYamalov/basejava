$(document).ready(function () {
    $('.add_period_button').on('click', function () {
        // Create a new companyContainer div
        let newPeriodContainer = $(this).prevAll('.period_container:first').clone(true);

        // Append the new companyContainer to the form
        $('#form').append(newPeriodContainer);

        // Move the newly added companyContainer before the clicked button
        $(this).before(newPeriodContainer);

        newPeriodContainer.find('input, textarea').val('');
    });
});

$(document).ready(function () {
    $('.add_education_button').click(function () {
        // Create a new companyContainer div
        let newCompanyContainer = $(this).prevAll('.companyContainer:first').clone(true);

        // Append the new companyContainer to the form
        $('#form').append(newCompanyContainer);

        // Move the newly added companyContainer before the clicked button
        $(this).before(newCompanyContainer);

        newCompanyContainer.find('input, textarea').val('');
    });
});

const form = document.querySelector("#form");

async function sendData() {
    // Select all company containers
    const educationContainers = document.querySelectorAll('.EDUCATION.companyContainer');
    const experienceContainers = document.querySelectorAll('.EXPERIENCE.companyContainer');

    // Initialize arrays to hold counts
    let educationPeriodCounts = [];
    let experiencePeriodCounts = [];

    // Function to count periods in a single container
    function countPeriods(container) {
        return container.querySelectorAll('.periodContainer').length;
    }

    // Count periods in each education container
    educationContainers.forEach((container) => {
        const count = countPeriods(container);
        educationPeriodCounts.push(count);
    });

    // Count periods in each experience container
    experienceContainers.forEach((container) => {
        const count = countPeriods(container);
        experiencePeriodCounts.push(count);
    });

    // Associate the FormData object with the form element
    const formData = new FormData(form);
    formData.append("educationPeriodCounts", educationPeriodCounts);
    formData.append("experiencePeriodCounts", experiencePeriodCounts);

    try {
        const response = await fetch("https://example.org/post", {
            method: "POST",
            // Set the FormData instance as the request body
            body: formData,
        });
        console.log(await response.json());
        console.log(educationCompanies.length)
    } catch (e) {
        console.error(e);
    }
}

// Take over form submission
form.addEventListener("submit", (event) => {
    event.preventDefault();
    sendData();
});