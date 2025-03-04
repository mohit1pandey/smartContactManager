console.log("loded")

const baseUrl="http://localhost:8080";

const viewContactModal = document.getElementById('view_contact_modal');

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
    contactModal.show();

}

function closeContactModal() {
    contactModal.hide();

}

async function loadContactModalData(id) {
    console.log(id);


    await fetch(`${baseUrl}/api/contact/${id}`)
        .then((response) => response.json()) // Return JSON response
        .then((data) => {
            console.log(data); // Log the response data
            // Handle the data (e.g., populate modal fields)


            // Populate modal with data
            // Populate modal with data 

            document.getElementById("contact_name").innerText=`${data.name||"name not found"}`

            document.getElementById("contact_img").innerHTML = `
                <img src="${data.cont_pricture}" alt="Profile Picture" class="w-24 h-24 rounded-full mx-auto" 
                  onerror="this.src='https://static.vecteezy.com/system/resources/previews/024/983/914/non_2x/simple-user-default-icon-free-png.png'">`;
            document.getElementById("contact_number").innerText = `Phone 📞 : ${data.phoneNumber || "N/A"}`;
            document.getElementById("contact_email").innerText = `Email 📧 : ${data.email || "N/A"}`;
            document.getElementById("contact_address").innerText = `Address 📍 : ${data.cont_address || "No Address"}`;
            document.getElementById("contact_description").innerText = `Description 📝 : ${data.cont_discription || "No Description"}`;

            document.getElementById("contact_links").innerHTML = `
                🔗 <a href="${data.cont_websiteLink}" class="text-blue-500 underline" target="_blank">Website</a><br>
             🔗 <a href="${data.cont_leinkedInLink}" class="text-blue-500 underline" target="_blank">LinkedIn</a>`;

            document.getElementById("contact_fav").innerText = data.fav ? "⭐ Favorite" : "☆ Not Favorite";


            contactModal.show();

            return data;
        })
        .catch((error) => console.error("Error fetching contact data:", error));


    // now show the contntac modal


}



    // const deleteContact =async (id)=>{
        // this code is copied form sweet icons


        async function deleteContact (id){

            Swal.fire({
                title: "Do you want to delete",
                text: "You won't be able to revert this!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, delete it!"
              }).then((result) => {
                if (result.isConfirmed) {
                  Swal.fire({
                    title: "Deleted!",
                    text: "Your file has been deleted.",
                    icon: "success"
                    
                  });
                  const url = `${baseUrl}/user/contacts/delete/${id}`;
                  window.location.replace(url);

                }
              });
            
        }



// ====================================v2====================================

const loadContactModalDataV2 = async (id) => {

    try {
        const response = await fetch(`http://localhost:8080/api/contact/${id}`)

        const data = await response.json();

        console.log(data);

        return data;


    } catch (error) {
        console.log("some error occured", error);

    }





}