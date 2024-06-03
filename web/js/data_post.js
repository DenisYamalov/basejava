$(document).ready(function () {
    $('.add_period_button').on('click', function () {
        // Create a new companyContainer div
        let newPeriodContainer = $(this).prevAll('.periodContainer:first').clone(true);

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
$(document).ready(function () {
    $('#form').submit(function (event) {
        event.preventDefault(); // Prevent the default form submission

        // Arrays to hold period counts for each type
        let educationPeriods = [];
        let experiencePeriods = [];

        // Function to count non-empty periods in a companyContainer
        function countNonEmptyPeriods(container) {
            let count = 0;
            container.find('.periodContainer').each(function () {
                let isEmpty = $(this).find('input, textarea').filter(function () {
                    return this.value.trim() === "";
                }).length > 0;
                if (!isEmpty) {
                    count++;
                }
            });
            return count;
        }

        // Iterate through each companyContainer
        $('.companyContainer').each(function () {
            let container = $(this);
            let periodCount = countNonEmptyPeriods(container);

            // Determine if it's EDUCATION or EXPERIENCE and add to appropriate array
            if (container.hasClass('EDUCATION')) {
                educationPeriods.push(periodCount);
            } else if (container.hasClass('EXPERIENCE')) {
                experiencePeriods.push(periodCount);
            }
        });

        educationPeriods.forEach((el, index, object) => {
            if (el !== 0) {
                $('<input>').attr({
                    type: 'hidden',
                    name: 'educationPeriods',
                    value: el
                }).appendTo('#form');
            } else {
                object.splice(index, 1);
            }
        });

        if (educationPeriods.length !== 0) {
            $('<input>').attr({
                type: 'hidden',
                name: 'EDUCATION',
                value: educationPeriods.length
            }).appendTo('#form');
        }


        experiencePeriods.forEach((el, index, object) => {
            if (el !== 0) {
                $('<input>').attr({
                    type: 'hidden',
                    name: 'experiencePeriods',
                    value: el
                }).appendTo('#form');
            } else {
                object.splice(index, 1);
            }
        });

        if (experiencePeriods.length !== 0) {
            $('<input>').attr({
                type: 'hidden',
                name: 'EXPERIENCE',
                value: experiencePeriods.length
            }).appendTo('#form');
        }
        // Now, allow the form to be submitted
        this.submit();
    });
});
$(document).ready(function (){
    $(".delete_button").on("click", function (){
        $(this.)
    })
});