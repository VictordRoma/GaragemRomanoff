import GrandPrixRepository from '../repositories/GrandPrixRepository.js';



class GrandPrixController {
    async index(req, res) {
        const grandPrixs = await GrandPrixRepository.list();
        return res.render('grandPrix/index', { layout: 'main', title: 'Grand Prix', grandPrixs: grandPrixs });
    }

    async store(req, res) {
        const { name, date, country } = req.body;
        const grandPrix = await GrandPrixRepository.create(name, date, country);
        if (grandPrix) {
            req.session.success = 'Grand Prix cadastrado com sucesso!';
            return res.redirect('/grandPrix');
        } else {
            req.session.error = 'Erro ao cadastrar Grand Prix!';
            return res.redirect('/grandPrix/create');
        }
    }

    async edit(req, res) {
        const grandPrix = await GrandPrixRepository.findById(req.params.id);
        return res.render('grandPrix/edit', { layout: 'main', title: 'Editar Grand Prix', grandPrix: grandPrix });
    }

    async update(req, res) {
        const { id, name, date, country } = req.body;
        const grandPrix = await GrandPrixRepository.update(id, name, date, country);
        if (grandPrix) {
            req.session.success = 'Grand Prix atualizado com sucesso!';
            return res.redirect('/grandPrix');
        } else {
            req.session.error = 'Erro ao atualizar Grand Prix!';
            return res.redirect('/grandPrix/edit/' + id);
        }
    }

    async delete(req, res) {
        const grandPrix = await GrandPrixRepository.delete(req.body.id);
        if (grandPrix) {
            req.session.success = 'Grand Prix deletado com sucesso!';
            return res.redirect('/grandPrix');
        } else {
            req.session.error = 'Erro ao deletar Grand Prix!';
            return res.redirect('/grandPrix');
        }
    }
}


export default new GrandPrixController();