if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

function ready() {
    document.cookie = 'magazinesWithAmount=; expires=-1; path=/';
    let removeCartItemButtons = document.getElementsByClassName('cart-delete-button');
    for (let i = 0; i < removeCartItemButtons.length; i++) {
        let button = removeCartItemButtons[i];
        button.addEventListener('click', removeFromCartClicked);
    }

    let quantityInputs = document.getElementsByClassName('cart-quantity-input');
    for (let i = 0; i < quantityInputs.length; i++) {
        let input = quantityInputs[i];
        input.addEventListener('change', quantityChanged);
    }

    let quantityUps = document.getElementsByClassName('cart-quantity-up');
    for (let i = 0; i < quantityUps.length; i++) {
        let up = quantityUps[i];
        up.addEventListener('click', quantityUp);
    }

    let quantityDowns = document.getElementsByClassName('cart-quantity-down');
    for (let i = 0; i < quantityDowns.length; i++) {
        let down = quantityDowns[i];
        down.addEventListener('click', quantityDown);
    }
    initCart();
}

let magazinesWithAmount = new Map();

function initCart() {
    updateLocalSum();
    updateLocalPeriod();
    updateQuantityTotal();
    updateCartTotal();
}

function removeFromCartClicked(event) {
    let buttonClicked = event.target;
    buttonClicked.parentElement.parentElement.parentElement.parentElement.remove();
    updateQuantityTotal();
    updateCartTotal();
    if (magazinesWithAmount.size === 0) {
        let phAlert = document.getElementById('empty-cart-alert');
        phAlert.innerHTML = '' +
            '<div class="alert alert-warning" role="alert"> You have not already add any item to your cart!</div>';
    }
}

function quantityUp(event) {
    let upClicked = event.target;
    let parentDiv = upClicked.parentElement.parentElement;
    let input = parentDiv.getElementsByClassName('cart-quantity-input')[0];
    input.value++;
    initCart();
}

function quantityDown(event) {
    let upClicked = event.target;
    let parentDiv = upClicked.parentElement.parentElement;
    let input = parentDiv.getElementsByClassName('cart-quantity-input')[0];
    input.value--;
    if (input.value <= 0) {
        input.value = 1;
    }
    initCart();
}

function quantityChanged(event) {
    let input = event.target;
    if (isNaN(input.value) || input.value <= 0) {
        input.value = 1;
    }
    input.value = Math.round(input.value);
    initCart();
}

function updateLocalSum() {
    let cartRows = document.getElementsByClassName('cart-row');
    for (let i = 0; i < cartRows.length; i++) {
        let cartRow = cartRows[i];
        let quantityStr = cartRow.getElementsByClassName('cart-quantity-input')[0].value;
        let quantity = parseInt(quantityStr);
        let priceWithDollar = cartRow.getElementsByClassName('cart-price')[0].innerText;
        let priceStr = priceWithDollar.slice(0, priceWithDollar.length);
        let price = parseFloat(priceStr);
        let totalPrice = quantity * price;
        totalPrice = Math.round(totalPrice * 100) / 100;
        cartRow.getElementsByClassName('local-sum')[0].innerText = 'Sum: ' + totalPrice + '$';
    }
}

function updateLocalPeriod() {
    let cartRows = document.getElementsByClassName('cart-row');
    for (let i = 0; i < cartRows.length; i++) {
        let cartRow = cartRows[i];
        let quantityStr = cartRow.getElementsByClassName('cart-quantity-input')[0].value;
        let quantity = parseInt(quantityStr);
        let periodWithDays = cartRow.getElementsByClassName('item-period-days')[0].innerText;
        let periodStr = periodWithDays.slice(0, periodWithDays.length - 4);
        let period = parseInt(periodStr);
        let totalPeriod = quantity * period;
        if (totalPeriod === 1) {
            cartRow.getElementsByClassName('local-period-subscription')[0].innerText = totalPeriod + 'day subscription';
        } else {
            cartRow.getElementsByClassName('local-period-subscription')[0].innerText = totalPeriod + 'days subscription';
        }
    }
}

function updateQuantityTotal() {
    let cartRows = document.getElementsByClassName('cart-row');
    let totalAmount = 0;
    for (let i = 0; i < cartRows.length; i++) {
        let cartRow = cartRows[i];
        let quantityStr = cartRow.getElementsByClassName('cart-quantity-input')[0].value;
        let quantity = parseInt(quantityStr);
        totalAmount = totalAmount + quantity;
    }
    document.getElementById('cart-total-amount').innerText = totalAmount + 'pc';
}

function updateCartTotal() {
    let cartRows = document.getElementsByClassName('cart-row');
    let total = 0;
    for (let i = 0; i < cartRows.length; i++) {
        let cartRow = cartRows[i];
        let priceWithDollar = cartRow.getElementsByClassName('cart-price')[0].innerText;
        let priceStr = priceWithDollar.slice(0, priceWithDollar.length);
        let price = parseFloat(priceStr);
        let quantity = cartRow.getElementsByClassName('cart-quantity-input')[0].value;

        let magazineId = cartRow.getElementsByClassName('cart-item-id')[0].value;
        magazinesWithAmount.set(magazineId, quantity);

        total = total + (price * quantity);
    }
    updateMap(cartRows);

    total = Math.round(total * 100) / 100;
    document.getElementById('cart-total-price').innerText = total + '$';

    checkAbilityToOrder(total);
}

function updateMap(cartRows) {
    let toDelete = [];
    for (let key of magazinesWithAmount.keys()) {
        let flag = true;
        for (let i = 0; i < cartRows.length; i++) {
            let magazineId = cartRows[i].getElementsByClassName('cart-item-id')[0].value;
            if (key === magazineId) {
                flag = false;
                break;
            }
        }
        if (flag) {
            toDelete.push(key);
        }
    }
    toDelete.forEach(function (element) {
        magazinesWithAmount.delete(element);
    })
}

function checkAbilityToOrder(cartPrice) {
    let orderButton = document.getElementById('submit-order-button');
    let orderButtonDisabled = orderButton.getAttribute('disabled');
    let currentBalance = document.getElementById('balance-field').value;
    let phAlert = document.getElementById('ph-to-alert');
    if (currentBalance === '-1') {
        if (orderButtonDisabled === null) {
            orderButton.setAttribute('disabled', 'disabled');
        }
        phAlert.innerHTML = '' +
            '<div class="alert alert-danger" role="alert">You can\'t order before sign in!</div>';
    } else if (currentBalance < cartPrice) {
        if (orderButtonDisabled === null) {
            orderButton.setAttribute('disabled', 'disabled');
        }
        phAlert.innerHTML = '' +
            '<div class="alert alert-danger" role="alert">You haven\'t enough money!</div>';
    } else if (cartPrice === 0) {
        if (orderButtonDisabled === null) {
            orderButton.setAttribute('disabled', 'disabled');
        }
        phAlert.innerHTML = '' +
            '<div class="alert alert-danger" role="alert">Cart is empty!</div>';
    } else {
        if (orderButtonDisabled === 'disabled') {
            orderButton.removeAttribute('disabled')
        }
        phAlert.innerHTML = '';
    }
}

window.onbeforeunload = function () {
    let outputCookie = '';
    for (let key of magazinesWithAmount.keys()) {
        outputCookie = outputCookie.concat(key, '-', magazinesWithAmount.get(key), '!');
    }
    if (magazinesWithAmount.size === 0) {
        document.cookie = 'magazinesWithAmount=-1; path=/';
    } else {
        document.cookie = 'magazinesWithAmount=' + outputCookie + '; path=/';
    }
};
