function updateCategoryOptions() {
    const complaintType = document.getElementById("complaintType").value;
    const categorySelect = document.getElementById("category");

    // Clear existing options
    categorySelect.innerHTML = "<option value=''>Select Category</option>";

    const categories = {
        Billing: ["Incorrect Bill", "Bill Due Date", "Current Rate", "Late Payment Fee"],
        Voltage: ["High Voltage Issue", "Low Voltage Issue", "Voltage Fluctuations"],
        Disruption: ["Frequent Outages", "Scheduled Outage", "Intermittent Power Supply"],
        StreetLight: ["Street Light Not Working", "Street Light Flickering", "Request New Street Light"],
        Pole: ["Leaning Pole", "Damaged Pole", "Request for Pole Relocation"]
    };

    if (categories[complaintType]) {
        categories[complaintType].forEach(cat => {
            const option = document.createElement("option");
            option.value = cat;
            option.textContent = cat;
            categorySelect.appendChild(option);
        });
    }
}

// Validate and submit the complaint form
function submitComplaint() {
    const consumerNo = document.getElementById("consumerNo").value;
    const mobile = document.getElementById("mobile").value;

    if (consumerNo.length !== 13) {
        alert("Consumer Number must be 13 digits.");
        return;
    }

    if (mobile.length !== 10) {
        alert("Mobile Number must be 10 digits.");
        return;
    }

    alert("Complaint Registered successfully! Your complaint ID is: " + Math.floor(Math.random() * 1000000));
    window.location.href = 'registerComplaint.html';
}

function resetForm() {
    // Reset the form fields
    document.getElementById("complaintForm").reset();

}

// Mock database of complaints
const complaints = [
    { id: '123456', status: 'In Progress' },
    { id: '234567', status: 'Resolved' },
    { id: '345678', status: 'Pending' }
];

// Function to check complaint status
function checkComplaintStatus() {
    const complaintId = document.getElementById("complaintId").value;
    const complaint = complaints.find(c => c.id === complaintId);

    const statusResult = document.getElementById("statusResult");
    
    if (complaint) {
        statusResult.innerHTML = `<p style='font-weight: bold;'>Complaint ID: ${complaint.id}</p><p style='font-weight: bold;'>Status: ${complaint.status}</p>`;
    } 
    else {
        statusResult.innerHTML = "<p style='color:red; font-weight: bold;'>Complaint ID not found.</p>";
    }
}