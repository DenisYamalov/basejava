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
// $(document).ready(function() {
//     let form = $('#form');
//     // Function to submit the form with additional data
//     async function submitFormWithCounts() {
//         let formData = new FormData(form[0]); // Get form data
//
//         // Select all company containers
//         const educationContainers = document.querySelectorAll('.EDUCATION.companyContainer');
//         const experienceContainers = document.querySelectorAll('.EXPERIENCE.companyContainer');
//
//         // Initialize arrays to hold counts
//         let educationPeriodCounts = [];
//         let experiencePeriodCounts = [];
//
//         // Function to count periods in a single container
//         function countPeriods(container) {
//             return container.querySelectorAll('.periodContainer').length;
//         }
//
//         // Count periods in each education container
//         educationContainers.forEach((container) => {
//             const count = countPeriods(container);
//             educationPeriodCounts.push(count);
//         });
//
//         // Count periods in each experience container
//         experienceContainers.forEach((container) => {
//             const count = countPeriods(container);
//             experiencePeriodCounts.push(count);
//         });
//
//         // Associate the FormData object with the form element
//         formData.append("educationPeriodCounts", educationPeriodCounts);
//         formData.append("experiencePeriodCounts", experiencePeriodCounts);
//
//         try {
//             const response = await fetch("https://example.org/post", {
//                 method: "POST",
//                 // Set the FormData instance as the request body
//                 // headers: {'Content-Type': 'application/x-www-form-urlencoded'},
//                 body: formData,
//             });
//             console.log(await response.json());
//             console.log(educationCompanies.length)
//         } catch (e) {
//             console.error(e);
//         }
//
//     }
//
//     // Attach event listener to the save button
//     $('button[type=submit]').click(function(e) {
//         e.preventDefault(); // Prevent default action
//         submitFormWithCounts();
//     });
//
//     // Optionally, attach event listener to reset button to prevent form submission
//     $('button[type=reset]').click(function(e) {
//         e.preventDefault(); // Prevent default action
//         window.history.back(); // Navigate back
//     });
// });

$(document).ready(function() {
    $('#form').submit(function(event) {
        var educationPeriods = [];
        var experiencePeriods = [];

        $('.EDUCATION.companyContainer').each(function() {
            var periodCount = $(this).find('.periodContainer').length;
            educationPeriods.push(periodCount);
        });

        $('.EXPERIENCE.companyContainer').each(function() {
            var periodCount = $(this).find('.periodContainer').length;
            experiencePeriods.push(periodCount);
        });

        $('<input>').attr({
            type: 'hidden',
            name: 'educationPeriods',
            value: JSON.stringify(educationPeriods)
        }).appendTo('#form');

        $('<input>').attr({
            type: 'hidden',
            name: 'experiencePeriods',
            value: JSON.stringify(experiencePeriods)
        }).appendTo('#form');
    });
});

// $(document).ready(function() {
//     $('#form').submit(function(event) {
//         var educationPeriods = {};
//         var experiencePeriods = {};
//
//         $('.EDUCATION.companyContainer').each(function(index) {
//             var periodCount = $(this).find('.periodContainer').length;
//             educationPeriods[index + 1] = periodCount; // Use 1-based keys
//         });
//
//         $('.EXPERIENCE.companyContainer').each(function(index) {
//             var periodCount = $(this).find('.periodContainer').length;
//             experiencePeriods[index + 1] = periodCount; // Use 1-based keys
//         });
//
//         $('<input>').attr({
//             type: 'hidden',
//             name: 'educationPeriods',
//             value: JSON.stringify(educationPeriods)
//         }).appendTo('#form');
//
//         $('<input>').attr({
//             type: 'hidden',
//             name: 'experiencePeriods',
//             value: JSON.stringify(experiencePeriods)
//         }).appendTo('#form');
//     });
// });