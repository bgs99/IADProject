import axios from 'axios';
import mockPeople from '../mock/mpeople';

export default {
  state: {
    people: []
  },
  mutations: {
    setPeople (state, list) {
      state.people = list;
    }
  },
  actions: {
    loadPeopleByLocation (context) {
      const id = context.getters.mapId;
      axios('/place/locals', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setPeople', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setPeople', mockPeople.filter(q => q.location.first === id));
        }
      })
    },
    loadPeopleByPage (context, page) {
      axios('/registry/people', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setPeople', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setPeople', mockPeople);
        }
      })
    }
  },
  getters: {
    people: state => state.people
  }
}
