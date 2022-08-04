package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LeilaoDaoTest {

    private LeilaoDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaCadastrarUmLeilao() {
        Leilao leilao = criarLeilao();
        Leilao salvo = dao.buscarPorId(leilao.getId());
        assertNotNull(salvo);
    }

    @Test
    void deveriaAtualizarUmLeilao() {
        Leilao leilao = criarLeilao();

        leilao.setNome("Celular");
        leilao.setValorInicial(new BigDecimal("400"));

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());
        assertEquals("Celular", salvo.getNome());
        assertEquals(new BigDecimal("400"), salvo.getValorInicial());
    }

    private Leilao criarLeilao() {
        Usuario usuario = criarUsuario();
        Leilao leilao = new Leilao("Mochila", new BigDecimal("70"), LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);
        return leilao;
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
        em.persist(usuario);
        return usuario;
    }
}