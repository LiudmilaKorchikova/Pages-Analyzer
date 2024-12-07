@template.layout.page(
    content = @`
        <h1>Конкретный URL</h1>

        <!-- Если есть flash-сообщение, показываем его -->
        <div class="alert alert-info" th:if="${flashMessage}" role="alert">
            ${flashMessage}
        </div>

        <p><strong>URL:</strong> ${url}</p>

        <a href="/urls" class="btn btn-primary">Назад к списку URL</a>
    `
)