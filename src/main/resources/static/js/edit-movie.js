new Vue({
  el: "#app",
  data: {
    name: data.name,
    description: data.description,
    poster: null,
    year: data.year,
    genres: [],
    selectedGenres: data.genres
  },
  created: function() {
    axios.get(MOVIES_URL + GENRES).then(response => {
      let genreList = response.data;
      genreList.forEach(genre => this.genres.push(genre));
    });
  },
  methods: {
    processForm: function() {
      let formData = new FormData();
      formData.append('name', this.name);
      formData.append('description', this.description);
      formData.append('poster', this.poster);
      formData.append('year', this.year);
      formData.append('genres', this.selectedGenres);

      instance.put(MOVIES_URL + "/" + data.id, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      })
      .then(function (response) {
        console.log(response);
        window.location.reload();
      })
      .catch(function (error) {
        console.log(error);
      });
    },
    handleFileUpload: function (event) {
      this.poster = event.target.files[0];
    }
  },
  computed: {
    getProfilePicture() {
      return data.user.profilePictureUrl;
    }
  }
});

