var viewmodel = new Vue({
    el: '#vuediv',
    methods: {
        load: function(id = 0) {
            axios('/place', {
                params:{
                  id: id
                },
                method: 'POST',
                auth: {
                    username: '18',
                    password: 'password'
                }
            }).then(response => {
                console.log(response);
                this.map = response.data;
            }).catch(error => {
                console.log(error);
            });
        }
    },
    beforeMount(){
        this.load();
    },
    data: {
        map: {}
    }
});
