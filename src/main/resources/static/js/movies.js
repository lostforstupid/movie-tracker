Vue.component('movie-view', {
  props: ['movie'],
  template: '<div class="movie">'
  + '<img :src="getImageSource">'
  + '<p>{{ movie.name }}</p>'
  + '<button v-if="ifMovieInLibrary" @click="addToUserLibrary()">Add to library</button>'
  + '<br>'
  + '<button @click="deleteMovie()">Delete</button>'
  + '</div>'
  + '</div>',
  methods: {
    deleteMovie: function () {
      axios.delete(MOVIES_URL + "/" + this.movie.id);
      window.location.reload();
    },
    addToUserLibrary: function () {
      axios.post(LIBRARY_URL + "/" + data.user.id
          + ADD_TO_LIBRARY_URL + this.movie.id);
    }
  },
  computed: {
    getImageSource() {
      return 'data:image/jpeg;base64,' + this.movie.poster;
    },
    ifMovieInLibrary() {
      console.log(this.movie.inUserLibrary);
      return !(this.movie.inUserLibrary);
    }
  }
});

Vue.component('movie-list', {
  props: ['movies'],
  template: '<div class="movie-list">'
  + '<movie-view v-for="movie in movies" :movie="movie" :key="movie.id"/>'
  + '</div>'
});

new Vue({
  el: '#app',
  template: '<movie-list :movies="movies"/>',
  data: {
    movies: []
  },
  created: function () {
    const params = {userId: data.user.id};

    axios.get(MOVIES_URL, { params }).then(response => {
      let movieList = response.data;
      movieList.forEach(movie => this.movies.push(movie))
    })
  }
});