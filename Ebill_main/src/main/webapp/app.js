// Calculate total amount in Pay Bill page
function calculateTotal() {
    const checkboxes = document.querySelectorAll('input[name="bill"]:checked');
    let total = 0;
    for(let i=0; i<checkboxes.length; i++) {
        total += parseInt(checkboxes[i].value);
    }

    // Display the total amount with a rupee sign
    document.getElementById("totalAmount").textContent = `₹${total}`;
    
    // Enable or disable the "Proceed to Pay" button based on the total amount
    document.getElementById("proceedButton").disabled = (total === 0);
}   

/*// Proceed to payment page
function proceedToPay() {
    const totalAmount = document.getElementById("totalAmount").textContent.replace('₹', ''); // Remove rupee sign
    if (parseInt(totalAmount) > 0) {
        localStorage.setItem("billAmount", totalAmount);
        window.location.href = 'payment.jsp';
    } else {
        alert("Please select at least one bill to proceed with payment.");
    }

}*/

/*//Payment page JS

// Retrieve the bill amount from localStorage
const billAmount = localStorage.getItem("billAmount");
const pgCharge = 50; // Fixed PG charge

// Initialize the page
document.getElementById("username").textContent = username;
document.getElementById("billAmount").textContent = `₹${billAmount}`;
const totalAmount = parseInt(billAmount) + pgCharge;
document.getElementById("totalAmount").textContent = `₹${totalAmount}`;

// Function to redirect to the home page
function redirectToHome() {
    window.location.href = 'home.html';
}

// Function to show credit card form
function proceedToPayment() {

    const paymentMode = document.getElementById("paymentMode").value;

    // Validate payment method selection
    if (paymentMode === "") {
        alert("Please select a payment method.");
        return;
    }

    document.getElementById("creditCardForm").classList.remove("hidden");
}

// Function to process payment
function makePayment() {
    const cardNumber = document.getElementById("cardNumber").value;
    const cardHolderName = document.getElementById("cardHolderName").value;
    const expiryDate = document.getElementById("expiryDate").value;
    const cvv = document.getElementById("cvv").value;

    // Basic validation
    function validateExpiryDate(expiryDate) {
        // Regular expression to check if the input follows MM/YY format
        const expDateRegex = /^(0[1-9]|1[0-2])\/\d{2}$/;
    
        // Check if the input matches the MM/YY format
        if (!expDateRegex.test(expiryDate)) {
            alert("Please enter a valid expiration date in MM/YY format.");
            return false;
        }
    
        // Extract month and year from the input
        const [month, year] = expiryDate.split('/').map(num => parseInt(num, 10));
        
        // Get the current date
        const currentDate = new Date();
        const currentMonth = currentDate.getMonth() + 1; // Months are 0-based
        const currentYear = parseInt(currentDate.getFullYear().toString().slice(-2), 10); // Last two digits of the year
    
        // Check if the expiration date is in the future
        if (year < currentYear || (year === currentYear && month < currentMonth)) {
            alert("The expiration date must be in the future.");
            return false;
        }
    
        return true;
    }

    if (cardNumber.length !== 16) {
        alert("Please enter 16 digits of the card.");
        return;
    }

    if (cardHolderName === "") {
        alert("Please enter a valid name");
        return;
    }

    // Validate the expiration date
    if (!validateExpiryDate(expiryDate)) {
        return; 
    }

    if (cvv.length !== 3) {
        alert("Please enter 3 digits of CVV.");
        return;
    }

    // Simulate a successful transaction
    document.getElementById("transactionInfo").textContent = `Payment of ₹${totalAmount} was successful using ${document.getElementById("paymentMode").value}.`;
    document.getElementById("transactionDetails").classList.remove("hidden");
    document.getElementById("downloadButton").classList.remove("hidden");
    document.getElementById("payInfo").classList.add("hidden");
    document.getElementById("creditCardForm").classList.add("hidden");
    document.getElementById("billHeader").classList.add("hidden");
}

// Function to download receipt
function downloadReceipt() {
    const link = document.createElement('a');
    link.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(document.getElementById("transactionInfo").textContent));
    link.setAttribute('download', 'receipt.txt');
    link.click();
}*/

// Complaint Registration page
// Populate category based on complaint type