import axios from 'axios';
import mockTransport from '../mock/mtransport';
import mockWeapons from '../mock/mweapons';

export default {
  state: {
    isWeapons: true,
    weapons: null,
    transport: null,
    page: 0
  },
  mutations: {
    setWeapons (state, list) {
      state.weapons = list;
    },
    setTransport (state, list) {
      state.transport = list;
    },
    setMode (state, mode) {
      state.isWeapons = mode;
    },
    setPage (state, page) {
      state.page = page;
    }
  },
  actions: {
    loadEquipmentByCar (context, id) {
      context.commit('setTabs', [], {root: true});
      axios('/transport', {
        params: {
          id: +id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setTransport', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setTransport', mockTransport.filter(q => +q.id === +id));
        }
      });
      context.commit('setMode', false);
    },
    loadEquipmentByTransport (context, page) {
      context.commit('setTabs', ['Weapons', 'Transport'], {root: true});
      axios('/transport', {
        method: 'GET'
      }).then(response => {
        context.commit('setTransport', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setTransport', mockTransport);
        }
      });
      context.commit('setMode', false);
      context.commit('setPage', +page);
    },
    loadEquipmentByGun (context, id) {
      context.commit('setTabs', [], {root: true});
      axios('/weapons', {
        params: {
          id: +id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setWeapons', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setWeapons', mockWeapons.filter(q => +q.id === +id));
        }
      });
      context.commit('setMode', true);
    },
    loadEquipmentByWeapons (context, page) {
      context.commit('setTabs', ['Weapons', 'Transport'], {root: true});
      axios('/weapons', {
        method: 'GET'
      }).then(response => {
        context.commit('setWeapons', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setWeapons', mockWeapons);
        }
      });
      context.commit('setMode', true);
      context.commit('setPage', +page);
    }
  },
  getters: {
    equipment: state => {
      const type = state.isWeapons ? 'weapons' : 'transport';
      if (!state[type]) {
        return [];
      }
      return state[type].length > 1
        ? state[type] : state[type].slice(state.page * 10, (state.page + 1) * 10);
    },
    weapons: state => state.weapons,
    transport: state => state.transport
  }
}
