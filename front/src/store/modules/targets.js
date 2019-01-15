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
    loadTargetsByLocation (context) {
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
          context.commit('setTargets', mockTargets);
        }
      })
    },
    loadTargetsByPage (context, page) {
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
          context.commit('setTargets', mockTargets);
        }
      })
    },
    loadTargetsById (context, id) {
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
