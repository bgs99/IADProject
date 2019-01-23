import axios from 'axios';
import mockPeople from '../mock/mpeople';

export default {
  state: {
    registry: []
  },
  mutations: {
    setPeople (state, list) {
      state.registry = list;
    }
  },
  actions: {
    makeTarget (context, {id, prs}) {
      axios(`registry/${prs ? 'people' : 'org'}/target`, {
        params: {
          id: id
        },
        method: 'POST'
      }).then(q => {
        const np = {
          ...context.state.registry.find(q => q.id === id),
          target: q.data
        };
        context.commit('setPeople', [...context.state.registry.filter(q => q.id !== id), np]);
      })
    },
    loadRegistryPeopleByLocation (context) {
      context.commit('setTabs', ['People', 'Organisations'], {root: true});
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
          context.commit('setPeople', mockPeople.filter(q => q.location.first === id && q.isPerson));
        }
      })
    },
    loadRegistryPeopleByPage (context, page) {
      context.commit('setTabs', ['People', 'Organisations'], {root: true});
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
          context.commit('setPeople', mockPeople.filter(q => q.isPerson));
        }
      })
    },
    loadRegistryOrganisationsByLocation (context) {
      context.commit('setTabs', ['People', 'Organisations'], {root: true});
      const id = context.getters.mapId;
      axios('/place/org', {
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
          context.commit('setPeople', mockPeople.filter(q => q.location.first === id && !q.isPerson));
        }
      })
    },
    loadRegistryOrganisationsByPage (context, page) {
      context.commit('setTabs', ['People', 'Organisations'], {root: true});
      axios('/registry/org', {
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
          context.commit('setPeople', mockPeople.filter(q => !q.isPerson));
        }
      })
    }
  },
  getters: {
    registry: state => state.registry
  }
}
