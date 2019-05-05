Vue.component('movie-view', {
  props: ['movie'],
  template: '<div class="movie">'
  + '<img :src="getImageSource">'
  + '<p>{{ movie.name }}</p>'
  // + '<button v-if="checkIfMovieInLibrary() === false" @click="addToUserLibrary()">Add to library</button>'
  + '<button @click="addToUserLibrary()">Add to library</button>'
  + '<br>'
  + '<button @click="deleteMovie()">Delete</button>'
  + '</div>'
  + '</div>',
  methods: {
    deleteMovie: function () {
      axios.delete(MOVIES_URL + "/" + this.movie.id);
      window.location.reload();
    },
    checkIfMovieInLibrary: function () {
      axios.get(LIBRARY_URL + "/" + data.user.id
          + CHECK_IF_EXISTS_URL + this.movie.id)
      .then(response => { return response.data });
    },
    addToUserLibrary: function () {
      axios.post(LIBRARY_URL + "/" + data.user.id
          + ADD_TO_LIBRARY_URL + this.movie.id);
    }
  },
  computed: {
    getImageSource() {
      return 'data:image/jpeg;base64,' + this.movie.poster;
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
    axios.get(MOVIES_URL).then(response => {
      let movieList = response.data;
      movieList.forEach(movie => this.movies.push(movie))
    })
  }
});