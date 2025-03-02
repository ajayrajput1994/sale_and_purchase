/* <div id="items-container"></div> add in html page for append inside product*/
let page = 0;
const loadMoreItems = () => {
    fetch(`/product/items?page=${page}&size=3`)
        .then(response => response.json())
        .then(data => {
            // const container = document.getElementById('items-container');
            data.content.forEach(item => {
                // const div = document.createElement('div');
                // div.textContent = item.name;
                $("#items-container").append(`<div class="card">
                  <div class="card-body"><div class="card-title">#${item.id} ${item.name} ${page}</div>
                    <p>${item.description}</p>
                  
                  </div></div>`);
            });
            page++;
        });
};

window.addEventListener('scroll', () => {
    if (window.scrollY + window.innerHeight >= document.documentElement.scrollHeight) {
        loadMoreItems();
    }
});

// Load initial items
loadMoreItems(); 