import axios from 'axios';
import mockTargets from '../mock/mtargets';

export default {
  state: {
    targets: []
  },
  mutations: {
    setTargets (state, list) {
      state.targets = list;
    }
  },
  actions: {
    loadTargetsPeopleByLocation (context) {
      context.commit('setTabs', ['People', 'Organisations'], {root: true});
      const id = context.getters.mapId;
      axios('/place/locals/targets', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTargets', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setTargets', mockTargets.filter(q => q.isPerson));
        }
      })
    },
    loadTargetsPeopleByPage (context, page) {
      context.commit('setTabs', ['People', 'Organisations'], {root: true});
      axios('/targets', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTargets', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setTargets', mockTargets.filter(q => q.isPerson));
        }
      })
    },
    loadTargetsOrganisationsByLocation (context) {
      context.commit('setTabs', ['People', 'Organisations'], {root: true});
      const id = context.getters.mapId;
      axios('/place/org/targets', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTargets', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setTargets', mockTargets.filter(q => !q.isPerson));
        }
      })
    },
    loadTargetsOrganisationsByPage (context, page) {
      context.commit('setTabs', ['People', 'Organisations'], {root: true});
      axios('/targets', {
        params: {
          page: page,
          people: false
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTargets', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setTargets', mockTargets.filter(q => !q.isPerson));
        }
      })
    },
    loadTargetsById (context, id) {
      context.commit('setTabs', [], {root: true});
      const lid = +id;
      axios('/targets', {
        params: {
          id: lid
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTargets', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setTargets', mockTargets.filter(q => +q.id === lid));
        }
      })
    }
  },
  getters: {
    targets: state => state.targets
  }
}
