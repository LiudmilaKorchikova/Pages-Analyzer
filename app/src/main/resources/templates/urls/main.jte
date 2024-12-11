@import hexlet.code.utils.NamedRoutes
@import hexlet.code.dto.BasePage
@param BasePage page = null

@template.layout.page(
    content = @`

        <!-- Форма для ввода URL -->
        <h2>Добавить URL</h2>
        <form action="${NamedRoutes.urlsPath()}" method="POST">
            <div class="form-group">
                <label for="url">Введите URL</label>
                <input type="text" class="form-control" id="url" name="url" placeholder="Введите URL" required>
            </div>
            <button type="submit" class="btn btn-primary">Добавить</button>
        </form>
    `
)