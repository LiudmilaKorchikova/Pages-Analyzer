@import hexlet.code.dto.urls.UrlPage

@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <div>
        <table class="table">
            <tbody>
            <tr>
                <td>Сайт:</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>ID</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${String.valueOf(page.getUrl().getCreatedAt())}</td>
            </tr>
            </tbody>
        </table>
    </div>
`
)