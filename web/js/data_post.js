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
        let educationPeriods = [];
        let experiencePeriods = [];

        $('.EDUCATION.companyContainer').each(function () {
            let periodCount = $(this).find('.periodContainer').length;
            educationPeriods.push(periodCount);
        });

        $('.EXPERIENCE.companyContainer').each(function () {
            let periodCount = $(this).find('.periodContainer').length;
            experiencePeriods.push(periodCount);
        });

        educationPeriods.forEach(el => {
            $('<input>').attr({
                type: 'hidden',
                name: 'educationPeriods',
                value: el
            }).appendTo('#form');
        });

        $('<input>').attr({
            type: 'hidden',
            name: 'EDUCATION',
            value: educationPeriods.length
        }).appendTo('#form');

        experiencePeriods.forEach(el => {
            $('<input>').attr({
                type: 'hidden',
                name: 'experiencePeriods',
                value: el
            }).appendTo('#form');
        });

        $('<input>').attr({
            type: 'hidden',
            name: 'EXPERIENCE',
            value: experiencePeriods.length
        }).appendTo('#form');
    });
});