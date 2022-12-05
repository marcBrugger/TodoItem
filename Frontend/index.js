window.addEventListener("load", () => {
    ToDo.init();
});

const ToDo = {
    toDoArray: [],
    addToDoInputActive: false,

    async init(){
        document.getElementById("ListAddItemContainer").addEventListener("click", () => {
            this.addToDo();
        });

        await fetch("http://127.0.0.1:8080/todos/")
        .then(res => {
            return res.json();
        })
        .then(data => {
            console.log(data);
            
            for(let todo of data){
                let div = document.createElement("div");
                div.classList.add("ListItemContainer");
                div.innerText = todo.todo;

                let svg = document.createElement("svg");
                svg.classList.add("checkToDoIcon");
                svg.innerHTML = '<svg width="60" height="60" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" > <path d="M10.5858 13.4142L7.75735 10.5858L6.34314 12L10.5858 16.2427L17.6568 9.1716L16.2426 7.75739L10.5858 13.4142Z" fill="#06c258" /> </svg>'
                svg.addEventListener("click", () => this.deleteToDo(todo.todo, svg.parentElement));

                div.appendChild(svg);
                document.getElementById("ListContainer").appendChild(div);
            }
        });
    },

    addToDo() {
        if(!this.addToDoInputActive) {
            let div = document.createElement("div");
            div.classList.add("ListItemContainer", "ListNewItemContainer");

            let input = document.createElement("input");
            input.classList.add("ListAddItemInput");
            input.onkeydown = (evt) => {
                if(evt.key == "Enter") {
                    if(input.value != "") {
                        let des = input.value;

                        evt.target.blur();
                        div.innerText = des;
                        div.classList.remove("ListNewItemContainer");
                        div.style.outline = "none";

                        document.getElementById("ListAddItemMessage").style.display = "none";

                        let svg = document.createElement("svg");
                        svg.classList.add("checkToDoIcon");
                        svg.innerHTML = '<svg width="60" height="60" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" > <path d="M10.5858 13.4142L7.75735 10.5858L6.34314 12L10.5858 16.2427L17.6568 9.1716L16.2426 7.75739L10.5858 13.4142Z" fill="#06c258" /> </svg>'

                        svg.addEventListener("click", () => this.deleteToDo(des, svg.parentElement));

                        div.appendChild(svg);

                        this.saveToDo(des);
                    }
                }
            }
            div.appendChild(input);

            this.addToDoInputActive = true;
            document.getElementById("ListContainer").appendChild(div);
            document.getElementById("ListAddItemMessage").style.display = "block";
            input.focus();
        } else {
            document.getElementsByClassName("ListNewItemContainer")[0].style.outline = "4px solid red";
        }
    },

    async saveToDo(des) {
        this.addToDoInputActive = false;

        await fetch("http://127.0.0.1:8080/todos/?name="+des, {
            method: "POST",
            headers: {'Content-Type': 'application/json'}
        })
        .then(res => {
            console.log(res);
        });
    },

    async deleteToDo(des, item) {
        item.remove();

        await fetch("http://127.0.0.1:8080/todos/?name="+des, {
            method: "DELETE",
            headers: {'Content-Type': 'application/json'}
        })
        .then(res => {
            console.log(res);
        });
    }
 }