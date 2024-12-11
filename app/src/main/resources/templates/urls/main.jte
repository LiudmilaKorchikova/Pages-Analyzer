@import hexlet.code.dto.urls.MainPage
@import hexlet.code.utils.NamedRoutes

@param MainPage page

@template.layout.page(
    page = page,
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