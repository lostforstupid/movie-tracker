Vue.component('movie-details', {
    props: ['name', 'description', 'poster'],
    template: '#movie-details',
    computed: {
        getImageSource() {
            return 'data:image/jpeg;base64,' + this.poster;
        },
        getProfilePicture() {
            return data.user.profilePictureUrl;
        }
    }
});

new Vue({
    el: '#app',
    template: '<movie-details :id="id" :name="name" :description="description" :poster="poster"/>',
    data: {
        id: '',
        name: '',
        description: '',
        poster: ''
    },
    created: function () {
        const params = {userId: data.user.id};

        axios.get(MOVIES_URL + "/" + data.id, {params}).then(response => {
            this.id = response.data.id;
            this.name = response.data.name;
            this.description = response.data.description;
            this.poster = response.data.poster;
        });
    }
});