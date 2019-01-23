import axios from 'axios'

export default {
  state: {
    repair: []
  },
  mutations: {
    setRepair (state, list) {
      state.repair = list;
    },
    startRepair (state, {id, time}) {
      const nr = {
        ...state.repair.find(q => q.id === id),
        end: time
      };
      state.repair = [
        ...state.repair.filter(q => q.id !== id),
        nr
      ];
    }
  },
  actions: {
    loadRepairTransportByPage (context, page) {
      context.commit('setTabs', [], {root: true});
      axios('/repairs/transport', {
        params: {
          page: page
        }
      }).then(q => {
        context.commit('setRepair', q.data);
      })
    },
    loadRepairWeaponsByPage (context, page) {
      context.commit('setTabs', [], {root: true});
      axios('/repairs/weapons', {
        params: {
          page: page
        }
      }).then(q => {
        context.commit('setRepair', q.data);
      })
    },
    startRepair (context, {id, iw}) {
      axios(`/repairs/${iw ? 'weapons' : 'transport'}/apply`, {
        params: {
          id: id
        },
        method: 'POST'
      }).then(q => {
        context.commit('startRepair', {id: id, time: q.data});
      })
    }
  },
  getters: {
    repair: state => state.repair
  }
}
