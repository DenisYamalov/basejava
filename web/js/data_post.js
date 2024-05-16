$(document).ready(function() {
    // Attach click event handler to.add_period_button buttons
    $(document).on('click', '.add_period_button', function(e) {
        e.preventDefault(); // Prevent the default action of the button

        // Find the closest.companyContainer and the last.periodContainer within it
        let $closestCompanyContainer = $(this).closest('.companyContainer');
        let $lastPeriodContainer = $closestCompanyContainer.find('.periodContainer:last');

        // Clone the last.periodContainer
        let $newPeriodContainer = $lastPeriodContainer.clone();

        // Append the cloned.periodContainer right after the last one
        $lastPeriodContainer.after($newPeriodContainer);

        // Optionally, clear the inputs in the new periodContainer
        $newPeriodContainer.find('input, textarea').val('');
    });
});

$(document).ready(function() {
    $('.add_education_button').click(function() {
        // Create a new clone of the existing companyContainer
        let newCompanyContainer = $('#companyContainer_1').clone();

        // Remove the ID from the cloned container to avoid duplicate IDs
        newCompanyContainer.attr('id', '');

        // Insert the cloned container before the current button
        $(this).before(newCompanyContainer);
    });
});

const form = document.querySelector("#form");

async function sendData() {
    // Select all company containers
    const educationContainers = document.querySelectorAll('.Education.companyContainer');
    const experienceContainers = document.querySelectorAll('.Experience.companyContainer');

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