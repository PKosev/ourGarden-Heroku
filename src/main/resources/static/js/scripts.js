$('#loadOrders').click(() => {
    reloadOrders()
});
$(function(){
    if($('body').is('.RestPage')){
        reloadOrders()
    }
});

function reloadOrders() {
    $("#authors-container").empty()
    fetchDatesAndOrders().then(([dates,orders])=>{
        dates.forEach(currentDate =>{
        let tableRow = '<div class="row align-content-start col-12 text-white bg-gradient rounded-top">' +
                '<div class="col-2">' +
                '<small>' + currentDate + '</small>' +
                '</div>' +
                '<div class="col-2">' +
                '<small>Заявено количество</small>' +
                '</div>' +
                '<div class="col-3">' +
                '<small>Приблизителна цена</small>' +
                '</div>' +
                '</div>';
                orders.forEach(currentOrder =>{
                    if (currentDate === currentOrder.dayEntity.date){
                        tableRow += '<div class="row align-content-start col-12 text-white">' +
                            '<div class="col-2">' +
                            '<small>' + currentOrder.dayEntity.product.nameInBulgarian + '</small>' +
                            '</div>' +
                            '<div class="col-2">' +
                            '<small>' + (currentOrder.quantity).toFixed(2) + ' кг</small>' +
                            '</div>' +
                            '<div class="col-3">' +
                            '<small>'+ (currentOrder.quantity * currentOrder.dayEntity.product.pricePerKilogram).toFixed(2) +' лева</small>' +
                            '</div>' +
                            '<div class="col-2">' +
                            '<button  data-order-id="' +currentOrder.id+ '"  class="link-button-update">Приключи</button>' +
                            '</div>'+
                            '</div>';
                    }
                })
        $("#authors-container").append(tableRow)
        } )
    })

}

async function fetchDatesAndOrders(){
    const [dateResponse, orderResponse] = await Promise.all([
        fetch("http://localhost:8080/rest/datesRest"),
        fetch("http://localhost:8080/rest/ordersRest")
    ]);

    const dates = await dateResponse.json();
    const orders = await orderResponse.json();

    return [dates, orders];
}

$('body').on('click', 'button.link-button-update', function () {
    let orderId = $(this).data('order-id');
    console.log("Order id to be updated is " + orderId);
    fetch('http://localhost:8080/rest/orders/' + orderId + '/ready', {
        method: 'GET'
    }).then(_ =>reloadOrders())
});