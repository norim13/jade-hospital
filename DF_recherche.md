# Introduction #

Pour rechercher un agent dans le DF, un agent doit suivre la procéduire suivante.


# Procédure #

DFAgentDescription template = new DFAgentDescription();
ServiceDescription sd = new ServiceDescription();
sd.setType("Ontologie");
sd.setName("KBAgent");
template.addServices(sd);

DFAgentDescription[.md](.md) result = null;
try {
> result = DFService.search(this, template);
} catch (FIPAException e) {
> System.out.println(e.getMessage());
> e.printStackTrace();
}

ArrayList

&lt;AID&gt;

 listeRetour = new ArrayList

&lt;AID&gt;

();
for(int i = 0; i < result.length; ++i)
> listeRetour.add(result[i](i.md).getName());