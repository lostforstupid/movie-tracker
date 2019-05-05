Vue.component('movie-view', {
  props: ['movie'],
  template: '<div class="movie">'
  + '<img :src="getImageSource">'
  + '<p>{{ movie.name }}</p>'
  + '<button @click="removeMovieFromLibrary()">Remove</button>'
  + '</div>',
  computed: {
    getImageSource() {
      return 'data:image/jpeg;base64,' + this.movie.poster;
    }
  },
  methods: {
    removeMovieFromLibrary: function () {
      axios.delete(LIBRARY_URL + "/" + data.user.id
          + REMOVE_FROM_LIBRARY_URL + this.movie.id);
      window.location.reload();
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
    axios.get(LIBRARY_URL + "/" + data.user.id).then(response => {
      let movieList = response.data;
      movieList.forEach(movie => this.movies.push(movie))
    })
  }
});