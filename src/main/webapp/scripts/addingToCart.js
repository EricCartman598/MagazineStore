if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', ready)
} else {
    ready()
}

function ready() {
    document.cookie = "magazinesIdInCart=; expires=-1; path=/";
    let removeCartItemButtons = document.getElementsByClassName('shop-delete-button');
    for (let i = 0; i < removeCartItemButtons.length; i++) {
        let button = removeCartItemButtons[i];
        button.addEventListener('click', removeFromCartClicked);
    }

    let addToCartButtons = document.getElementsByClassName('shop-add-button');
    for (let i = 0; i < addToCartButtons.length; i++) {
        let button = addToCartButtons[i];
        button.addEventListener('click', addToCartClicked);
    }
    initCart();
}

function initCart() {
    let itemsInSessionCart = document.getElementsByClassName('shop-delete-button');
    for (let i = 0; i < itemsInSessionCart.length; i++) {
        let shopItem = itemsInSessionCart[i].parentElement.parentElement;
        let magazineId = shopItem.getElementsByClassName('shop-item-id')[0].value;
        magazinesInCart.push(magazineId);
    }
    console.log(magazinesInCart);
}

let magazinesInCart = [];

function removeFromCartClicked(event) {
    let button = event.target;
    let shopItem = button.parentElement.parentElement;
    let magazineId = shopItem.getElementsByClassName('shop-item-id')[0].value;
    removeItemFromCart(magazineId);
    changeStateToAdd(button.parentElement);
}

function removeItemFromCart(magazineId) {
    for (let i = 0; i < magazinesInCart.length; i++) {
        if (magazinesInCart[i] === magazineId) {
            magazinesInCart.splice(i, 1);
            updateCart();
            return;
        }
    }
}

function changeStateToAdd(phButton) {
    phButton.innerHTML = '' +
        '<button type="button" class="btn btn-success shop-add-button" >To cart</button>';
    phButton.getElementsByClassName('shop-add-button')[0].addEventListener('click', addToCartClicked);
}

function addToCartClicked(event) {
    let button = event.target;
    let shopItem = button.parentElement.parentElement;
    let magazineId = shopItem.getElementsByClassName('shop-item-id')[0].value;
    addItemToCart(magazineId);
    changeStateToDel(button.parentElement);
}

function addItemToCart(magazineId) {
    for (let i = 0; i < magazinesInCart.length; i++) {
        if (magazinesInCart[i] === magazineId) {
            return;
        }
    }
    magazinesInCart.push(magazineId);
}

function changeStateToDel(phButton) {
    phButton.innerHTML = '' +
        '<button type="button" class="btn btn-danger shop-delete-button" >Remove</button>';
    phButton.getElementsByClassName('shop-delete-button')[0].addEventListener('click', removeFromCartClicked);
}

window.onbeforeunload = function () {
    if (magazinesInCart.length === 0) {
        magazinesInCart.push(-1);
    }
    document.cookie = "magazinesIdInCart=" + magazinesInCart.join("!") + "; path=/";
};
