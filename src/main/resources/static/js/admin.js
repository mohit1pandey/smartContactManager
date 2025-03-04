/* here we will make a js logic to show selected image to form */


console.log("admin page loggedin")



document.getElementById('image_file_input').addEventListener('change', function (event) {
    const file = event.target.files[0]; // Get selected file
    if (file) {
        const reader = new FileReader(); // Create FileReader
        reader.onload = function (e) {
            const img = document.getElementById('upload_image_preview');
            img.src = e.target.result; // Set image source
            img.style.display = 'block'; // Show image
        };
        reader.readAsDataURL(file); // Read file as data URL
    }
});