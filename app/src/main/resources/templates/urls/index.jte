@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.utils.NamedRoutes

@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`
        <h1>Список Url</h1>
        <div>
            <table class="table">
            <thead>
            <tr>
                <th>ID</th>
                <th>URL</th>
                <th>Дата создания</th>
            </tr>
            </thead>
            <tbody>
            @for(var url : page.getUrls())

                    <tr>
                        <td>${url.getId()}</td>
                        <td><a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a></td>
                        <td>${String.valueOf(url.getCreatedAt())}</td>
                    </tr>

            @endfor
            </tbody>
            </table>
        </div>
    `
)