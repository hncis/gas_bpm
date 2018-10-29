/* Written by jhlee (jhlee@appsoulute.com). */
(function( factory ) {
    if ( typeof define === "function" && define.amd ) {

        // AMD. Register as an anonymous module.
        define([ "../jquery.ui.datepicker" ], factory );
    } else {

        // Browser globals
        factory( jQuery.datepicker );
    }
}(function( datepicker ) {
    datepicker.regional['ko-KR'] = {
        closeText: '닫기',
        prevText: '&#x3C;이전 월',
        nextText: '다음 월&#x3E;',
        currentText: '오늘',
        monthNames: ['1월','2월','3월','4월','5월','6월',
            '7월','8월','9월','10월','11월','12월'],
        monthNamesShort: ['1월','2월','3월','4월','5월','6월',
            '7월','8월','9월','10월','11월','12월'],
        dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'],
        dayNamesShort: ['일주일','월요일','화요일','수요일','목요일','금요일','토요일'],
        dayNamesMin: ['일','월','화','수','목','금','토'],
        weekHeader: '주',
        dateFormat: 'yy-mm-dd',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: true,
        yearSuffix: '년'};
    datepicker.setDefaults(datepicker.regional['ko-KR']);

    return datepicker.regional['ko-KR'];

}));