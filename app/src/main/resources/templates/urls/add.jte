@template.layout.page(
    content = @`
        <h1>Urls</h1>

        <!-- Отображение flash-сообщения -->
        <div class="alert alert-info" th:if="${flashMessage}" role="alert">
            ${flashMessage}
        </div>

        <!-- Форма для ввода URL -->
        <h2>Добавить URL</h2>
        <form action="/add-url" method="POST">
            <div class="form-group">
                <label for="url">Введите URL</label>
                <input type="text" class="form-control" id="url" name="url" placeholder="Введите URL" required>
            </div>
            <button type="submit" class="btn btn-primary">Добавить</button>
        </form>
    `
)