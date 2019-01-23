import axios from 'axios';
import mockAgents from '../mock/magents';

export default {
  state: {
    agents: []
  },
  mutations: {
    setAgents (state, list) {
      state.agents = list;
    },
    setSalary (state, {id, wage}) {
      state.agents.find(q => q.id === id).salary = wage;
    },
    rank (state, {id, d}) {
      state.agents.find(q => q.id === id).level += d;
    }
  },
  actions: {
    loadAgentsByLocation (context) {
      context.commit('setTabs', [], {root: true});
      const id = context.getters.mapId;
      axios('/place/locals/agents', {
        params: {
          id: id
        },
        method: 'GET'
      }).then(response => {
        context.commit('setAgents', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setAgents', mockAgents);
        }
      })
    },
    loadAgentsByPage (context, page) {
      context.commit('setTabs', [], {root: true});
      axios('/agents', {
        params: {
          page: page
        },
        method: 'GET'
      }).then(response => {
        context.commit('setAgents', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setAgents', mockAgents);
        }
      })
    },
    loadAgentsById (context, id) {
      context.commit('setTabs', [], {root: true});
      const lid = +id;
      axios('/agents', {
        params: {
          id: lid
        },
        method: 'GET'
      }).then(response => {
        context.commit('setAgents', response.data);
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        } else {
          context.commit('setAgents', mockAgents.filter(q => +q.id === lid));
        }
      })
    },
    updateSalary (context, {id, wage}) {
      const lid = +id;
      const oldWage = context.state.agents.find(q => q.id === lid).salary;
      if (oldWage === +wage) {
        return;
      }
      axios('/agents/wage/set', {
        params: {
          id: lid,
          wage: +wage
        },
        method: 'POST'
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        }
      });
      context.commit('setSalary', {id: lid, wage: +wage});
    },
    promote (context, id) {
      const lid = +id;
      axios('/agents/promote', {
        params: {
          id: lid
        },
        method: 'POST'
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        }
      });
      context.commit('rank', {id: lid, d: +1});
    },
    demote (context, id) {
      const lid = +id;
      axios('/agents/demote', {
        params: {
          id: lid
        },
        method: 'POST'
      }).catch(error => {
        if (!context.getters.mock) {
          console.log(error);
        }
      });
      context.commit('rank', {id: lid, d: -1});
    }
  },
  getters: {
    agents: state => state.agents
  }
}
