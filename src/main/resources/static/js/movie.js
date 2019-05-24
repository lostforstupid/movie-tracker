Vue.component('movie-details', {
    props: ['name', 'description', 'poster', 'year', 'genres'],
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
    template: '<movie-details :id="id" :name="name" :description="description" ' +
        ':poster="poster" :year="year" :genres="genres"/>',
    data: {
        id: '',
        name: '',
        description: '',
        poster: '',
        year: '',
        genres: []
    },
    created: function () {
        const params = {userId: data.user.id};

        axios.get(MOVIES_URL + "/" + data.id, {params}).then(response => {
            this.id = response.data.id;
            this.name = response.data.name;
            this.description = response.data.description;
            this.poster = response.data.poster;
            this.year = response.data.year;
            this.genres = response.data.genres;
        });
    }
});